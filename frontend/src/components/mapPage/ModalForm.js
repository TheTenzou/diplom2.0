import React from 'react';
import { Modal, Button } from 'bootstrap-4-react';

const OpenModalForm = () => {
  return(
    <div>
      <Button primary data-toggle="modal" data-target="#modalForm">Добавить новый маркер</Button>

      <Modal id="modalForm" fade>
        <Modal.Dialog centered>
          <Modal.Content>
            <Modal.Header>
              <Modal.Title>Добавьте свойства маркера</Modal.Title>
              <Modal.Close>
                <span aria-hidden="true">&times;</span>
              </Modal.Close>
            </Modal.Header>
            <Modal.Body>
              Здесь форма со свойствами маркера
            </Modal.Body>
            <Modal.Footer>
              <Button secondary data-dismiss="modal">Отменить</Button>
              <Button primary>Добавить</Button>
            </Modal.Footer>
          </Modal.Content>
        </Modal.Dialog>
      </Modal>
    </div>
  );
};

export default OpenModalForm;
