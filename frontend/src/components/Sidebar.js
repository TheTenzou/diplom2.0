import React, { Component} from 'react';
import { Collapse, Card, Form, Button } from 'bootstrap-4-react';

export default class MySidebar extends Component {
  render() {
    return (
      <div>
        <React.Fragment>

          <p>
            <Collapse.Button primary target="#sort1" aria-expanded="false" aria-controls="sort1" mx="auto" mt="3" style={{display: "block"}}>
              Сортировка 1
            </Collapse.Button>

            <br/>

            <Collapse.Button primary target="#sort2" aria-expanded="false" aria-controls="sort2" mx="auto" style={{display: "block"}}>
              Сортировка 2
            </Collapse.Button>
          </p>

          <Collapse id="sort1">
            <Card>
              <Card.Body>
                Пока что тестовые сортировочки 1
              </Card.Body>

              <Card.Footer>
                <Form>
                  <Form.Group>
                    <Form.Check>
                      <Form.CheckInput type="checkbox" id="testCheck1" />
                      <Form.CheckLabel htmlFor="testCheck1">чекбокс1</Form.CheckLabel>
                    </Form.Check>
                  </Form.Group>

                  <Form.Group>
                    <label htmlFor="testSelect1">селект1</label>
                    <Form.Select id="testSelect1">
                      <option>1</option>
                      <option>2</option>
                      <option>3</option>
                    </Form.Select>
                  </Form.Group>

                  <Form.Group>
                    <Form.Check>
                      <Form.Radio id="testRadio1" name="testRadio" value="option1" defaultChecked />
                      <Form.CheckLabel htmlFor="defaultCheck1">радио1</Form.CheckLabel>
                    </Form.Check>
                    <Form.Check>
                      <Form.Radio id="testRadio2" name="testRadio" value="option2" />
                      <Form.CheckLabel htmlFor="defaultCheck2">радио2</Form.CheckLabel>
                    </Form.Check>
                  </Form.Group>

                  <Button primary type="submit">Принять</Button>
                </Form>
              </Card.Footer>
            </Card>
          </Collapse>

          <Collapse id="sort2">
            <Card>
              <Card.Body>
                Вот тут сортировочки номер 2
              </Card.Body>

              <Card.Footer>
                <Form>
                  <Form.Group>
                    <Form.Check>
                      <Form.CheckInput type="checkbox" id="testCheck2" />
                      <Form.CheckLabel htmlFor="testCheck2">чекбокс2</Form.CheckLabel>
                    </Form.Check>
                  </Form.Group>

                  <Form.Group>
                    <label htmlFor="testSelect2">селект2</label>
                    <Form.Select id="testSelect2">
                      <option>1</option>
                      <option>2</option>
                      <option>3</option>
                    </Form.Select>
                  </Form.Group>

                  <Form.Group>
                    <Form.Check>
                      <Form.Radio id="testRadio3" name="testRadio2" value="option1" defaultChecked />
                      <Form.CheckLabel htmlFor="defaultCheck1">радио3</Form.CheckLabel>
                    </Form.Check>
                    <Form.Check>
                      <Form.Radio id="testRadio4" name="testRadio2" value="option2" />
                      <Form.CheckLabel htmlFor="defaultCheck2">радио4</Form.CheckLabel>
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
