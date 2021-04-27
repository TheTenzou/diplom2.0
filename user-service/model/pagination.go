package model

type Pagination struct {
	Limit  int64    `json:"limit"`
	Page   int64    `json:"page"`
	Roles  []string `json:"sort"`
	Status []string `json:"status"`
}
