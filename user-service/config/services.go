package config

import (
	"github.com/TheTenzou/diplom2.0/user-service/interfaces"
	"github.com/TheTenzou/diplom2.0/user-service/service"
)

type Services struct {
	UserService interfaces.UserService
}

// init all necessary services
func InitServices(repositories *Repositories) *Services {
	userService := service.NewUserService(repositories.UserRepository)

	return &Services{
		UserService: userService,
	}
}
