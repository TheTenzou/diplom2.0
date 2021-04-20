package requests

import "go.mongodb.org/mongo-driver/bson/primitive"

type UpdateUserRequest struct {
	ID       primitive.ObjectID `json:"id" binding:"required"`
	Login    string             `json:"login" binding:"required"`
	Password string             `json:"password" binding:"required,gte=6,lte=30"`
	Name     string             `json:"name" binding:"omitempty"`
}
