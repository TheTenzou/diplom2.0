package model

type PaginatedData struct {
	Total      int64       `json:"total"`
	Page       int64       `json:"page"`
	PageSize   int64       `json:"pageSize"`
	Previous   int64       `json:"previousPage"`
	Next       int64       `json:"next"`
	TotalPages int64       `json:"totalPages"`
	Data       interface{} `json:"data"`
}
