package config

import (
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

	userRepository := repository.NewMongoUserRepository(mongo)

	return &Repositories{
		UserRepository: userRepository,
	}, nil
}
