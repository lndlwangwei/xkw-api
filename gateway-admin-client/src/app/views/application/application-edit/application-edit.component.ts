import {Component, OnInit} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {ApplicationService} from '../../../common/service/application.service';

@Component({
  selector: 'app-application-edit',
  templateUrl: './application-edit.component.html',
})
export class ApplicationEditComponent implements OnInit {

  public application: any = {};
  public isConfirmed = false;

  constructor(public modalRef: BsModalRef, public applicationService: ApplicationService) {
  }

  ngOnInit() {
  }

  public save() {
    this.applicationService.save(this.application).subscribe(response => {
      this.isConfirmed = true;
      this.modalRef.hide();
    });
  }

  genSecret() {
    const secret = 'abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ~!-_?*&^%#@()';
    const selen = secret.length - 1;
    let i = 0;
    let password = '';
    while (i < 32) {
      password += secret[Math.round(Math.random() * selen)];
      i++;
    }
    this.application.secret = password;
  }
}
