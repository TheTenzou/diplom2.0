import React, { Component} from 'react';
import { Collapse, Card, Form, Button } from 'bootstrap-4-react';

export default class MySidebar extends Component {
  render() {
    return (
      <div>
        <React.Fragment>

          <p>
            <Collapse.Button primary target="#sort1" aria-expanded="false" aria-controls="sort1" mx="auto" mt="3" style={{display: "block"}}>
              Сортировка ТСОДД
            </Collapse.Button>

            <br/>

            <Collapse.Button primary target="#sort2" aria-expanded="false" aria-controls="sort2" mx="auto" style={{display: "block"}}>
              Сортировка Потоков
            </Collapse.Button>
          </p>

          <Collapse id="sort1">
            <Card>
              <Card.Body>
                Выборка ТСОДД элементов на карте
              </Card.Body>

              <Card.Footer>
                <Form>
                  <Form.Group>
                    Состояние объектов
                    <Form.Check>
                      <Form.CheckInput type="checkbox" id="testCheck1" />
                      <Form.CheckLabel htmlFor="testCheck1">Отличное</Form.CheckLabel>
                    </Form.Check>
                    <Form.Check>
                      <Form.CheckInput type="checkbox" id="testCheck2" />
                      <Form.CheckLabel htmlFor="testCheck2">Приемлемое</Form.CheckLabel>
                    </Form.Check>
                    <Form.Check>
                      <Form.CheckInput type="checkbox" id="testCheck3" />
                      <Form.CheckLabel htmlFor="testCheck3">Проблема</Form.CheckLabel>
                    </Form.Check>
                  </Form.Group>

                  <Form.Group>
                    <label htmlFor="testSelect1">Тип объекта</label>
                    <Form.Select id="testSelect1">
                      <option>Светофоры</option>
                      <option>Дорожные знаки</option>
                      <option>Информационные щиты</option>
                      <option>Указатели</option>
                    </Form.Select>
                  </Form.Group>

                  <Button primary type="submit">Принять</Button>
                </Form>
              </Card.Footer>
            </Card>
          </Collapse>

          <Collapse id="sort2">
            <Card>
              <Card.Body>
                Выборка потоков на карте
              </Card.Body>

              <Card.Footer>
                <Form>
                  <Form.Group>
                    <Form.Check>
                      <Form.CheckInput type="checkbox" id="testCheck4" />
                      <Form.CheckLabel htmlFor="testCheck4">Транспортные потоки</Form.CheckLabel>
                    </Form.Check>
                    <Form.Check>
                      <Form.CheckInput type="checkbox" id="testCheck5" />
                      <Form.CheckLabel htmlFor="testCheck5">Пешеходные потоки</Form.CheckLabel>
                    </Form.Check>
                  </Form.Group>

                  <Button primary type="submit">Принять</Button>
                </Form>
              </Card.Footer>
            </Card>
          </Collapse>

        </React.Fragment>
      </div>
    );
  }
}
