package main

import (
	"context"
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"

	"github.com/TheTenzou/diplom2.0/user-service/config"
	"github.com/TheTenzou/diplom2.0/user-service/config/databases"
)

func main() {
	log.Printf("Stating server....")

	repositories, err := config.InitRepositories()
	if err != nil {
		log.Fatalf("Unable to initialize repositories: %v\n", err)
	}

	services := config.InitServices(repositories)

	handler := config.InitHandlers(services)

	server := &http.Server{
		Addr:    ":8080",
		Handler: handler,
	}

	gracefullShutdown(server)
}

func gracefullShutdown(server *http.Server) {
	// Graceful server shutdown - https://github.com/gin-gonic/examples/blob/master/graceful-shutdown/graceful-shutdown/server.go
	go func() {
		if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Fatalf("Failed to initialize server: %v\n", err)
		}
	}()

	log.Printf("Listening on port %v\n", server.Addr)

	// Wait for kill signal of channel
	quit := make(chan os.Signal, 1)

	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)

	// This blocks until a signal is passed into the quit channel
	<-quit

	// The context is used to inform the server it has 5 seconds to finish
	// the request it is currently handling
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	mongo, err := databases.GetMongo()
	if err != nil {
		log.Fatalf("FAild to close mongo: %v\n", err)
	}
	mongo.Disconnect(ctx)

	// Shutdown server
	log.Println("Shutting down server...")
	if err := server.Shutdown(ctx); err != nil {
		log.Fatalf("Server forced to shutdown: %v\n", err)
	}
}
