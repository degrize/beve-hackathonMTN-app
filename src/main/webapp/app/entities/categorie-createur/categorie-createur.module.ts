import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategorieCreateurComponent } from './list/categorie-createur.component';
import { CategorieCreateurDetailComponent } from './detail/categorie-createur-detail.component';
import { CategorieCreateurUpdateComponent } from './update/categorie-createur-update.component';
import { CategorieCreateurDeleteDialogComponent } from './delete/categorie-createur-delete-dialog.component';
import { CategorieCreateurRoutingModule } from './route/categorie-createur-routing.module';

@NgModule({
  imports: [SharedModule, CategorieCreateurRoutingModule],
  declarations: [
    CategorieCreateurComponent,
    CategorieCreateurDetailComponent,
    CategorieCreateurUpdateComponent,
    CategorieCreateurDeleteDialogComponent,
  ],
})
export class CategorieCreateurModule {}
