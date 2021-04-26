package model

import "go.mongodb.org/mongo-driver/bson/primitive"

// define document structure in database
// define structure of json
type User struct {
	ID       primitive.ObjectID `bson:"_id,omitempty" json:"id,omitempty"`
	Login    string             `bson:"login,omitempty" json:"login,omitempty"`
	Password string             `bson:"password,omitempty" json:"-"`
	Name     string             `bson:"name,omitempty" json:"name,omitempty"`
	Status   string             `bson:"status,omitempty" json:"status"`
	Role     []string           `bson:"roles,omitempty" json:"role,omitempty"`
}
