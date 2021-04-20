package config

import (
	"context"
	"fmt"
	"log"
	"os"
	"time"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type DataSources struct {
	MongoDB *mongo.Client
}

func ConfigDataSources() (*DataSources, error) {

	mongoClient, err := configMongo()
	if err != nil {
		return nil, fmt.Errorf("faild to connect to mongo: %v", err)
	}

	return &DataSources{
		MongoDB: mongoClient,
	}, nil
}

func (ds *DataSources) Close() error {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	err := ds.MongoDB.Disconnect(ctx)
	if err != nil {
		return fmt.Errorf("error closing mongo: %v", err)
	}

	return nil
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
