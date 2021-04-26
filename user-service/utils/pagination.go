package utils

import (
	"strconv"

	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/gin-gonic/gin"
)

func GenaratePaginationFromRequest(ctx *gin.Context) model.Pagination {
	limit := 10
	page := 1
	roles := []string{"user", "admin"}
	query := ctx.Request.URL.Query()

	for key, value := range query {
		switch key {
		case "limit":
			limit, _ = strconv.Atoi(value[0])
		case "page":
			page, _ = strconv.Atoi(value[0])

		case "roles":
			roles = value
		}
	}

	return model.Pagination{
		Limit: limit,
		Page:  page,
		Roles: roles,
	}
}
