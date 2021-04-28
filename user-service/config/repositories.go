package config

import (
	"os"

	"github.com/TheTenzou/gis-diplom/user-service/config/databases"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/repository"
)

type Repositories struct {
	UserRepository  interfaces.UserService
	TokenRepository interfaces.TokenRepository
}

// initialise all necassary repositories
func InitRepositories() (*Repositories, error) {

	mongo, err := databases.GetMongo()
	if err != nil {
		return nil, err
	}

	dbName := os.Getenv("MONGO_DABASE_NAME")
	collectionName := os.Getenv("USERS_COLLECTION")
	usersCollection := mongo.Database(dbName).Collection(collectionName)

	userRepository := repository.NewMongoUserRepository(usersCollection)

	redis, err := databases.GetRedis()
	if err != nil {
		return nil, err
	}

	tokenRepository := repository.NewTokenRepository(redis)

	return &Repositories{
		UserRepository:  userRepository,
		TokenRepository: tokenRepository,
	}, nil
}
