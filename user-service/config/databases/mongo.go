package databases

import (
	"context"
	"fmt"
	"log"
	"os"
	"time"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var mongoDB *mongo.Client

func GetMongo() (*mongo.Client, error) {
	if mongoDB == nil {
		var err error
		mongoDB, err = configMongo()
		if err != nil {
			return nil, fmt.Errorf("error connecting to monog: %v", err)
		}
	}

	return mongoDB, nil
}

func configMongo() (*mongo.Client, error) {

	log.Printf("Conecting to mongo...")

	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	mongoHost := os.Getenv("MONGO_HOST")
	mongoPort := os.Getenv("MONGO_PORT")

	mongoURL := fmt.Sprintf("mongodb://%s:%s", mongoHost, mongoPort)

	mongoClient, err := mongo.Connect(ctx, options.Client().ApplyURI(mongoURL))
	if err != nil {
		return nil, err
	}

	log.Printf("Connected to mongo")

	return mongoClient, nil
}
