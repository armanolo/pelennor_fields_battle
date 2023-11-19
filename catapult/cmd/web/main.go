package main

import (
	"log"
	"net/http"
	"os"
)

type application struct {
	generation string
	available  bool
}

func main() {
	log.Println("Start cannon")
	generationId := os.Getenv("GENERATION")

	if generationId == "" {
		generationId = "1"
	}

	app := application{generation: generationId, available: true}

	hand := app.routes()

	http.ListenAndServe(":3000", hand)

}
