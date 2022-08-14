import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReseauxSociaux } from '../reseaux-sociaux.model';
import { ReseauxSociauxService } from '../service/reseaux-sociaux.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './reseaux-sociaux-delete-dialog.component.html',
})
export class ReseauxSociauxDeleteDialogComponent {
  reseauxSociaux?: IReseauxSociaux;

  constructor(protected reseauxSociauxService: ReseauxSociauxService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reseauxSociauxService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
