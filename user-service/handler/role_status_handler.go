package handler

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

// временное решение дляя ролей и состояний пользователя
func InitStatusAndRoleHandler(router *gin.Engine) {

	group := router.Group("/api/users/v1")

	group.GET("/roles", getRoles)
	group.GET("/userStatus", getUserStatusList)
}

func getRoles(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, []string{"ROLE_USER", "ROLE_ADMIN"})
}

func getUserStatusList(ctx *gin.Context) {
	ctx.JSON(http.StatusOK, []string{"ACTIVE", "DELETED"})
}
