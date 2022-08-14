import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InspirationComponent } from './list/inspiration.component';
import { InspirationDetailComponent } from './detail/inspiration-detail.component';
import { InspirationUpdateComponent } from './update/inspiration-update.component';
import { InspirationDeleteDialogComponent } from './delete/inspiration-delete-dialog.component';
import { InspirationRoutingModule } from './route/inspiration-routing.module';

@NgModule({
  imports: [SharedModule, InspirationRoutingModule],
  declarations: [InspirationComponent, InspirationDetailComponent, InspirationUpdateComponent, InspirationDeleteDialogComponent],
})
export class InspirationModule {}
