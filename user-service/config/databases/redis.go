package databases

import (
	"context"
	"fmt"
	"log"
	"os"

	"github.com/go-redis/redis/v8"
)

var redisDB *redis.Client

// GetRedis singleton return mongo data source
func GetRedis() (*redis.Client, error) {
	if redisDB == nil {
		var err error
		redisDB, err = configRedis()
		if err != nil {
			return nil, fmt.Errorf("error conncting to redis: %v", err)
		}
	}

	return redisDB, nil
}

// connect to redis
func configRedis() (*redis.Client, error) {

	log.Printf("Connecting to redis...")

	redisHost := os.Getenv("REDIS_HOST")
	redisPort := os.Getenv("REDIS_PORT")

	redisClient := redis.NewClient(&redis.Options{
		Addr:     fmt.Sprintf("%s:%s", redisHost, redisPort),
		Password: "",
		DB:       0,
	})

	_, err := redisClient.Ping(context.Background()).Result()
	if err != nil {
		return nil, fmt.Errorf("error connecting to redis: %v", err)
	}

	log.Printf("Connected to redis")

	return redisClient, nil
}
