package config

import (
	"os"

	"github.com/TheTenzou/diplom2.0/user-service/config/databases"
	"github.com/TheTenzou/diplom2.0/user-service/interfaces"
	"github.com/TheTenzou/diplom2.0/user-service/repository"
)

type Repositories struct {
	UserRepository interfaces.UserService
}

func InitRepositories() (*Repositories, error) {

	mongo, err := databases.GetMongo()
	if err != nil {
		return nil, err
	}

	dbName := os.Getenv("MONGO_DABASE_NAME")
	collectionName := os.Getenv("USERS_COLLECTION")
	usersCollection := mongo.Database(dbName).Collection(collectionName)

	userRepository := repository.NewMongoUserRepository(usersCollection)

	return &Repositories{
		UserRepository: userRepository,
	}, nil
}
