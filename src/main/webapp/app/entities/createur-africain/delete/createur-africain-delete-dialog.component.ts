import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICreateurAfricain } from '../createur-africain.model';
import { CreateurAfricainService } from '../service/createur-africain.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './createur-africain-delete-dialog.component.html',
})
export class CreateurAfricainDeleteDialogComponent {
  createurAfricain?: ICreateurAfricain;

  constructor(protected createurAfricainService: CreateurAfricainService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.createurAfricainService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
