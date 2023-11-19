package main

import (
	"log"
	"net/http"
	"time"

	"github.com/go-chi/chi/middleware"
	"github.com/go-chi/chi/v5"
)

func checkTime(t time.Time) {
	log.Println(time.Since(t))
}

func (app *application) routes() http.Handler {

	t := time.Now()
	defer checkTime(t)

	mux := chi.NewRouter()
	// register log
	mux.Use(middleware.Logger)

	// register middleware
	mux.Use(middleware.Recoverer)
	mux.Use(app.addIPToContext)

	// register routes
	mux.Post("/fire", app.Fire)
	mux.Get("/status", app.Status)

	return mux
}
