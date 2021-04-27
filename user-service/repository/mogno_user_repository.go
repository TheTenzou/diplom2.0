package repository

import (
	"context"
	"log"

	"github.com/TheTenzou/gis-diplom/user-service/apperrors"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/model"
	mongopagination "github.com/gobeam/mongo-go-pagination"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

type mongoUserRepository struct {
	Users *mongo.Collection
}

// factry for initializating user repository
func NewMongoUserRepository(usersCollection *mongo.Collection) interfaces.UserRepository {
	return &mongoUserRepository{
		Users: usersCollection,
	}
}

// fetch user by id from databse
// userID is id of requested user
func (r *mongoUserRepository) FindByID(
	ctx context.Context,
	userID primitive.ObjectID,
) (model.User, error) {

	var user model.User

	err := r.Users.FindOne(ctx, model.User{ID: userID}).Decode(&user)
	if err != nil {
		return model.User{}, apperrors.NewNotFound("id", userID.Hex())
	}

	return user, nil
}

// fetch user by login from databse
// userLogin is login of requested user
func (r *mongoUserRepository) FindByLogin(
	ctx context.Context,
	userLogin string,
) (model.User, error) {

	var user model.User

	err := r.Users.FindOne(ctx, model.User{Login: userLogin}).Decode(&user)
	if err != nil {
		return user, apperrors.NewNotFound("login", userLogin)
	}

	return user, nil
}

// return page of users
// page is number of requested page
// pageSize is size of page
func (r *mongoUserRepository) FindAll(
	ctx context.Context,
	pagination model.Pagination,
) (model.PaginatedData, error) {
	var users []model.User

	var filter bson.M
	if len(pagination.Roles) > 0 {
		filter = bson.M{"roles": bson.M{"$all": pagination.Roles}, "status": bson.M{"$in": pagination.Status}}
	} else {
		filter = bson.M{"status": bson.M{"$in": pagination.Status}}
	}

	paginatedData, err :=
		mongopagination.
			New(r.Users).
			Context(ctx).
			Limit(pagination.Limit).
			Page(pagination.Page).
			Filter(filter).
			Decode(&users).
			Find()
	if err != nil {
		log.Printf("faild to fetch users %v", err)
		return model.PaginatedData{}, apperrors.NewInternal()
	}

	log.Printf("resutl: %v", users)

	log.Printf("resutl: %v", paginatedData)

	return model.PaginatedData{
		Total:      paginatedData.Pagination.Total,
		Page:       paginatedData.Pagination.Page,
		PageSize:   paginatedData.Pagination.PerPage,
		Previous:   paginatedData.Pagination.Next,
		Next:       paginatedData.Pagination.Next,
		TotalPages: paginatedData.Pagination.TotalPage,
		Data:       users,
	}, nil
}

// create user record in data base
// user login shoud be unique
// return inserted use with id
func (r *mongoUserRepository) Create(
	ctx context.Context,
	user model.User,
) (model.User, error) {
	log.Println("1")

	user.Status = "active"

	userID, err := r.Users.InsertOne(ctx, user)
	if err != nil {
		if err, ok := err.(mongo.WriteException); ok && err.HasErrorMessage("duplicate key error collection") {
			log.Printf(
				"Could not create a user with login: %v. Reason %v\n",
				user.Login,
				"duplicate key error collection",
			)
		}
		return model.User{}, apperrors.NewConflict("login", user.Login)
	}

	user.ID = userID.InsertedID.(primitive.ObjectID)

	return user, nil
}

// update user record in database
// user login shoud be unique
func (r *mongoUserRepository) Update(
	ctx context.Context,
	user model.User,
) (model.User, error) {

	updateResult, err := r.Users.UpdateByID(ctx, user.ID, bson.M{"$set": user})

	if updateResult.MatchedCount == 0 {
		log.Printf("Couldn't update user. Id %v doesn't exit.", user.ID)
		return model.User{}, apperrors.NewNotFound("id", user.ID.Hex())
	}

	if err != nil {
		if err, ok := err.(mongo.WriteException); ok && err.HasErrorMessage("duplicate key error collection") {
			log.Printf(
				"Could not update a user with new login: %v. Reason %v\n",
				user.Login,
				"duplicate key error collection",
			)
		}
		return model.User{}, apperrors.NewConflict("login", user.Login)
	}

	var updatedUser model.User

	err = r.Users.FindOne(ctx, model.User{ID: user.ID}).Decode(&updatedUser)
	if err != nil {
		return model.User{}, apperrors.NewNotFound("id", user.ID.Hex())
	}

	return updatedUser, nil
}

// mark user is deleted
// return deleted user
func (r *mongoUserRepository) Delete(
	ctx context.Context,
	userID primitive.ObjectID,
) (model.User, error) {

	updateResult, err := r.Users.UpdateByID(ctx, userID, bson.M{"$set": bson.M{"status": "deleted"}})

	if updateResult.MatchedCount == 0 {
		log.Printf("Couldn't update user. Id %v doesn't exit.", userID)
		return model.User{}, apperrors.NewNotFound("id", userID.Hex())
	}

	if err != nil {
		return model.User{}, apperrors.NewConflict("login", userID.Hex())
	}

	var updatedUser model.User

	err = r.Users.FindOne(ctx, model.User{ID: userID}).Decode(&updatedUser)
	if err != nil {
		return model.User{}, apperrors.NewNotFound("id", userID.Hex())
	}

	return updatedUser, nil
}
