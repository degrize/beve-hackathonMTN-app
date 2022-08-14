import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorieCreateur } from '../categorie-createur.model';
import { CategorieCreateurService } from '../service/categorie-createur.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './categorie-createur-delete-dialog.component.html',
})
export class CategorieCreateurDeleteDialogComponent {
  categorieCreateur?: ICategorieCreateur;

  constructor(protected categorieCreateurService: CategorieCreateurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieCreateurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
