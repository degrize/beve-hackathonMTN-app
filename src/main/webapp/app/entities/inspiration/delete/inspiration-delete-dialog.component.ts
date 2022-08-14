import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInspiration } from '../inspiration.model';
import { InspirationService } from '../service/inspiration.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './inspiration-delete-dialog.component.html',
})
export class InspirationDeleteDialogComponent {
  inspiration?: IInspiration;

  constructor(protected inspirationService: InspirationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inspirationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
