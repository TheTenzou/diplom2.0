package utils

import (
	"strconv"

	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/gin-gonic/gin"
)

func GenaratePaginationFromRequest(ctx *gin.Context) model.Pagination {
	var limit int64 = 10
	var page int64 = 1
	// roles := []string{"user", "admin"}
	roles := []string{}
	status := []string{"active"}

	query := ctx.Request.URL.Query()

	for key, value := range query {
		switch key {
		case "limit":
			limit, _ = strconv.ParseInt(value[0], 10, 64)
		case "page":
			page, _ = strconv.ParseInt(value[0], 10, 64)
		case "roles":
			roles = value
		case "status":
			status = value
		}
	}

	return model.Pagination{
		Limit:  limit,
		Page:   page,
		Roles:  roles,
		Status: status,
	}
}
