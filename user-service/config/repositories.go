package config

import (
	"github.com/TheTenzou/diplom2.0/user-service/interfaces"
	"github.com/TheTenzou/diplom2.0/user-service/repository"
)

type Repositories struct {
	UserRepository interfaces.UserService
}

func InitRepositories(dataSources *DataSources) *Repositories {
	userRepository := repository.NewMongoUserRepository(dataSources.MongoDB)

	return &Repositories{
		UserRepository: userRepository,
	}
}
