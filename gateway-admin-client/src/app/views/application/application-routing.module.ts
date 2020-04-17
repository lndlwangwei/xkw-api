import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {ApplicationComponent} from './application.component';

const routes: Routes = [
  {
    path: '',
    component: ApplicationComponent,
    data: {
      title: 'application'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationRoutingModule {
}
