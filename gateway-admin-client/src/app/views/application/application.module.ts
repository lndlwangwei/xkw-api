import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ApplicationRoutingModule} from './application-routing.module';
import {ApplicationComponent} from './application.component';
import {ApplicationService} from '../../common/service/application.service';
import {ApplicationEditComponent} from './application-edit/application-edit.component';
import {MyCommonModule} from '../../common/my-common.module';
import {FormsModule} from '@angular/forms';
import {AlertModule, ModalModule} from 'ngx-bootstrap';


@NgModule({
  declarations: [ApplicationComponent, ApplicationEditComponent],
  imports: [
    CommonModule,
    MyCommonModule,
    ModalModule.forRoot(),
    AlertModule.forRoot(),
    FormsModule,
    ApplicationRoutingModule
  ],
  providers: [
    ApplicationService
  ],
  entryComponents: [
    ApplicationEditComponent
  ]
})
export class ApplicationModule {
}
