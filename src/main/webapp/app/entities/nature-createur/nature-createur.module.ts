import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatureCreateurComponent } from './list/nature-createur.component';
import { NatureCreateurDetailComponent } from './detail/nature-createur-detail.component';
import { NatureCreateurUpdateComponent } from './update/nature-createur-update.component';
import { NatureCreateurDeleteDialogComponent } from './delete/nature-createur-delete-dialog.component';
import { NatureCreateurRoutingModule } from './route/nature-createur-routing.module';

@NgModule({
  imports: [SharedModule, NatureCreateurRoutingModule],
  declarations: [
    NatureCreateurComponent,
    NatureCreateurDetailComponent,
    NatureCreateurUpdateComponent,
    NatureCreateurDeleteDialogComponent,
  ],
})
export class NatureCreateurModule {}
