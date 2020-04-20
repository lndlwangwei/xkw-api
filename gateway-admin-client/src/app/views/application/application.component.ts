import {Component, OnInit} from '@angular/core';
import {ApplicationService} from '../../common/service/application.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {ApplicationEditComponent} from './application-edit/application-edit.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
})
export class ApplicationComponent implements OnInit {

  applications = [];
  appModalRef: BsModalRef;

  constructor(private applicationService: ApplicationService, private modalService: BsModalService,
              public router: Router) {
  }

  ngOnInit() {
    this.getApplications();
  }

  public getApplications() {
    this.applicationService.getAll().subscribe(response => {
      this.applications = response;
    });
  }

  openEditModal(application?) {
    this.appModalRef = this.modalService.show(ApplicationEditComponent, {
      backdrop: 'static',
      initialState: {application: application || {}}
    });

    this.modalService.onHidden.subscribe(r => {
      if (this.appModalRef.content.isConfirmed) {
        this.getApplications();
      }
    });
  }

  managePermission(application) {
    this.router.navigate(['/api-group', {appId: application.id}]);
  }
}
