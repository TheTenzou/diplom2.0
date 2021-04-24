package mocks

import (
	"context"

	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/stretchr/testify/mock"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type MockUserService struct {
	mock.Mock
}

func (m *MockUserService) FindByID(ctx context.Context, userID primitive.ObjectID) (model.User, error) {
	args := m.Called(ctx, userID)

	var r0 model.User
	if args.Get(0) != nil {
		r0 = args.Get(0).(model.User)
	}

	var r1 error

	if args.Get(1) != nil {
		r1 = args.Get(1).(error)
	}

	return r0, r1
}

func (m *MockUserService) FindByLogin(ctx context.Context, userLogin string) (model.User, error) {

	args := m.Called(ctx, userLogin)

	var r0 model.User
	if args.Get(0) != nil {
		r0 = args.Get(0).(model.User)
	}

	var r1 error

	if args.Get(1) != nil {
		r1 = args.Get(1).(error)
	}

	return r0, r1
}

func (m *MockUserService) FindAll(ctx context.Context, page int /*, pageSize int*/) ([]model.User, error) {

	args := m.Called(ctx, page)

	var r0 []model.User
	if args.Get(0) != nil {
		r0 = args.Get(0).([]model.User)
	}

	var r1 error

	if args.Get(1) != nil {
		r1 = args.Get(1).(error)
	}

	return r0, r1
}

func (m *MockUserService) Create(ctx context.Context, user model.User) (model.User, error) {

	args := m.Called(ctx, user)

	var r0 model.User
	if args.Get(0) != nil {
		r0 = args.Get(0).(model.User)
	}

	var r1 error

	if args.Get(1) != nil {
		r1 = args.Get(1).(error)
	}

	return r0, r1
}

func (m *MockUserService) Update(ctx context.Context, user model.User) error {

	args := m.Called(ctx, user)

	var r0 error

	if args.Get(0) != nil {
		r0 = args.Get(0).(error)
	}

	return r0
}

func (m *MockUserService) Delete(ctx context.Context, userID primitive.ObjectID) (model.User, error) {

	args := m.Called(ctx, userID)

	var r0 model.User
	if args.Get(0) != nil {
		r0 = args.Get(0).(model.User)
	}

	var r1 error

	if args.Get(1) != nil {
		r1 = args.Get(1).(error)
	}

	return r0, r1
}
