import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReseauxSociauxComponent } from './list/reseaux-sociaux.component';
import { ReseauxSociauxDetailComponent } from './detail/reseaux-sociaux-detail.component';
import { ReseauxSociauxUpdateComponent } from './update/reseaux-sociaux-update.component';
import { ReseauxSociauxDeleteDialogComponent } from './delete/reseaux-sociaux-delete-dialog.component';
import { ReseauxSociauxRoutingModule } from './route/reseaux-sociaux-routing.module';

@NgModule({
  imports: [SharedModule, ReseauxSociauxRoutingModule],
  declarations: [
    ReseauxSociauxComponent,
    ReseauxSociauxDetailComponent,
    ReseauxSociauxUpdateComponent,
    ReseauxSociauxDeleteDialogComponent,
  ],
})
export class ReseauxSociauxModule {}
