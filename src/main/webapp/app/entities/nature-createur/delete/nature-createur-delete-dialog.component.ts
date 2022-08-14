import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureCreateur } from '../nature-createur.model';
import { NatureCreateurService } from '../service/nature-createur.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './nature-createur-delete-dialog.component.html',
})
export class NatureCreateurDeleteDialogComponent {
  natureCreateur?: INatureCreateur;

  constructor(protected natureCreateurService: NatureCreateurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureCreateurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
