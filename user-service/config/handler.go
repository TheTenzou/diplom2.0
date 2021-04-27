package config

import (
	"github.com/TheTenzou/gis-diplom/user-service/handler"
	"github.com/gin-gonic/gin"
)

// init handler with all necessary routs
func InitHandlers(services *Services) *gin.Engine {

	router := gin.Default()

	handler.InitUserHandler(router, services.UserService)
	handler.InitAuthHandler(router, handler.AuthHandlerConfig{
		AuthService: services.AuthService,
	})

	return router
}
