package main

import (
	"bytes"
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"
	"time"
)

func Test_Application_Fire(t *testing.T) {
	app := application{generation: "1", available: true}
	t.Log(app)
	server := httptest.NewTLSServer(app.routes())
	//Close server when finish the tests
	defer server.Close()

	content_type := "application/json; charset=UTF-8"
	status_path := "/fire"

	tests := []struct {
		body     string
		code     int
		expected string
	}{
		{
			`{"target": { "x": 0,"y": 40 },"enemies":1}`,
			202,
			"{\"casualties\":1,\"generation\":1}\n",
		},
		{
			`{"no-field": 40,"enemies":1}`,
			400,
			"json: unknown field \"no-field\"\n",
		},
	}

	var body *strings.Reader
	var expected_code int
	var expected_test string

	for _, test := range tests {
		body = strings.NewReader(test.body)
		expected_code = test.code
		expected_test = test.expected

		resp, err := server.Client().Post(server.URL+status_path, content_type, body)

		if resp.StatusCode != expected_code {
			t.Errorf("For %q the code expected was %d but got %d",
				status_path, expected_code, resp.StatusCode)
		}

		if err != nil {
			t.Errorf("For %q the status code is %d", status_path, resp.StatusCode)
		}

		buf := new(bytes.Buffer)
		buf.ReadFrom(resp.Body)
		//result := json.NewDecoder(bytes.NewReader(buf.Bytes())).Decode(&message)

		if buf.String() != expected_test {
			t.Errorf("Test expected was %q but got %q", expected_test, buf.String())
		}
	}

}

func Test_Application_Status(t *testing.T) {
	app := application{generation: "1", available: true}
	server := httptest.NewTLSServer(app.routes())
	defer server.Close()

	expected_code := 200
	status_path := "/status"
	expected_test := "{\"generation\":1,\"available\":true}\n"

	resp, err := server.Client().Get(server.URL + status_path)

	if resp.StatusCode != expected_code {
		t.Errorf("For %q the code expected was %d but got %d",
			status_path, expected_code, resp.StatusCode)
	}

	if err != nil {
		t.Errorf("For %q the status code is %d", status_path, resp.StatusCode)
	}

	buf := new(bytes.Buffer)
	buf.ReadFrom(resp.Body)

	if buf.String() != expected_test {
		t.Errorf("Test expected was %q but got %q", expected_test, buf.String())
	}
}

type FireACheck struct {
	action  string
	request string
	result  string
	time    int
}

func Test_Application_Both(t *testing.T) {
	app := application{generation: "2", available: true}
	server := httptest.NewTLSServer(app.routes())
	defer server.Close()

	tests := []FireACheck{
		{
			action: "/status",
			result: "{\"generation\":2,\"available\":true}\n",
			time:   0,
		},
		{
			action:  "/fire",
			request: `{"target": { "x": 0,"y": 40 },"enemies":1}`,
			result:  "{\"casualties\":1,\"generation\":2}\n",
			time:    0,
		},
		{
			action: "/status",
			result: "{\"generation\":2,\"available\":false}\n",
			time:   500,
		},
		{
			action: "/status",
			result: "{\"generation\":2,\"available\":true}\n",
			time:   2000,
		},
	}

	c := make(chan string)
	for _, step := range tests {
		go call_client(t, server, step, c)
	}
	for range tests {
		t.Log(<-c)
	}
}

func call_client(t *testing.T, server *httptest.Server, job FireACheck, c chan string) {
	var resp *http.Response
	if job.action == "/fire" {
		content_type := "application/json; charset=UTF-8"
		body := strings.NewReader(job.request)
		resp, _ = server.Client().Post(server.URL+job.action, content_type, body)
	} else {
		if job.time > 0 {
			time.Sleep(time.Duration(job.time) * time.Millisecond)
		}
		resp, _ = server.Client().Get(server.URL + job.action)
	}
	c <- job.action
	buf := new(bytes.Buffer)
	buf.ReadFrom(resp.Body)
	if buf.String() != job.result {
		t.Errorf("Test expected was %q but got %q", job.result, buf.String())
	}
}
