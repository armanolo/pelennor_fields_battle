package main

import (
	"encoding/json"
	"log"
	"net/http"
	"strconv"
	"time"
)

/*
	{
		"target": { "x": 0,"y": 40 },
		"enemies":1
	}
*/
type Request struct {
	Target struct {
		X int `json:"x"`
		Y int `json:"y"`
	} `json:"target"`
	Enemies    int `json:"enemies"`
	Generation int `json:"generation"`
}

type ResponseStatus struct {
	Generation int  `json:"generation"`
	Available  bool `json:"available"`
}

type ResponseFire struct {
	Casualties int `json:"casualties"`
	Generation int `json:"generation"`
}

func (app *application) fireAndLoading(id int) {
	//- **Catapult 1**: 1st Generation, Fire Time 3.5s
	//- **Catapult 2**: 2nd Generation, Fire Time 1.5s
	//- **Catapult 3**: 3rd Generation, Fire Time 2.5s
	var time_sleep time.Duration

	time_sleep = 3500
	switch id {
	case 2:
		time_sleep = 1500
	case 3:
		time_sleep = 2500
	default:
		{
		}
	}
	app.available = false
	log.Printf("Sleep duration: %q", time_sleep)
	time.Sleep(time_sleep * time.Millisecond)
	app.available = true
}

func (app *application) getGenerationId() (int, error) {
	gId, err_conver := strconv.Atoi(app.generation)

	return gId, err_conver
}

func (app *application) Fire(w http.ResponseWriter, r *http.Request) {
	//h := r.Header.Get("Content-Type")
	//_ = h
	gId, err_conver := app.getGenerationId()
	if err_conver != nil {
		http.Error(w, err_conver.Error(), http.StatusBadRequest)
	}

	go app.fireAndLoading(gId)

	var m Request
	decoder := json.NewDecoder(r.Body)
	decoder.DisallowUnknownFields()

	if err := decoder.Decode(&m); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	out := ResponseFire{Casualties: m.Enemies, Generation: gId}
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.WriteHeader(http.StatusAccepted)
	if err := json.NewEncoder(w).Encode(out); err != nil {
		panic(err)
	}
	// "casualties": 1,"generation": 1
}

func (app *application) Status(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.WriteHeader(http.StatusOK)

	gId, err_conver := app.getGenerationId()
	if err_conver != nil {
		http.Error(w, err_conver.Error(), http.StatusInternalServerError)
	}

	out := ResponseStatus{Generation: gId, Available: app.available}
	if err := json.NewEncoder(w).Encode(out); err != nil {
		panic(err)
	}
}
