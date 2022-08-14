import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CreateurAfricainComponent } from './list/createur-africain.component';
import { CreateurAfricainDetailComponent } from './detail/createur-africain-detail.component';
import { CreateurAfricainUpdateComponent } from './update/createur-africain-update.component';
import { CreateurAfricainDeleteDialogComponent } from './delete/createur-africain-delete-dialog.component';
import { CreateurAfricainRoutingModule } from './route/createur-africain-routing.module';

@NgModule({
  imports: [SharedModule, CreateurAfricainRoutingModule],
  declarations: [
    CreateurAfricainComponent,
    CreateurAfricainDetailComponent,
    CreateurAfricainUpdateComponent,
    CreateurAfricainDeleteDialogComponent,
  ],
})
export class CreateurAfricainModule {}
