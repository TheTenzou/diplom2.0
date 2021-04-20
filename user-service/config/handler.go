package config

import (
	"github.com/TheTenzou/diplom2.0/user-service/handler"
	"github.com/gin-gonic/gin"
)

func InitHandlers(services *Services) *gin.Engine {

	router := gin.Default()

	handler.InitUserHandler(router, services.UserService)

	return router
}
