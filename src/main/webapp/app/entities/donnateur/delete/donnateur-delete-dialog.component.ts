import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDonnateur } from '../donnateur.model';
import { DonnateurService } from '../service/donnateur.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './donnateur-delete-dialog.component.html',
})
export class DonnateurDeleteDialogComponent {
  donnateur?: IDonnateur;

  constructor(protected donnateurService: DonnateurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.donnateurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
