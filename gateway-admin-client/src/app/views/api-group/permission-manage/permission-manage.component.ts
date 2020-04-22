import {Component, OnInit, ViewChild} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {ApiService} from '../../../common/service/api.service';
import {ZtreeComponent} from '../../../common/ztree/ztree.component';
import {AppPermissionUtils} from '../../../common/utils/AppPermissionUtils';
import {AlertService} from '../../../common/alert/alert.service';
import {ApplicationService} from '../../../common/service/application.service';

@Component({
  selector: 'app-permission-manage',
  templateUrl: './permission-manage.component.html',
})
export class PermissionManageComponent implements OnInit {

  constructor(public modalRef: BsModalRef, public apiService: ApiService,
              public applicationService: ApplicationService, public alertService: AlertService) {
  }

  apiGroup: any;
  appId: string;
  apiPrefix: string;

  public isConfirmed = false;

  znodes = [];
  expStr = '';
  @ViewChild('permTree', {static: false})
  private permTree: ZtreeComponent;
  private treeId: string;

  zsetting = {
    data: {
      simpleData: {
        enable: true,
        idKey: 'id',
        pIdKey: 'parentId'
      },
      key: {
        title: 'description'
      }
    },
    check: {
      enable: true
    },
    callback: {
      onCheck: (event, treeId, treeNode) => {
        this.onCheckHandler(event, treeId, treeNode);
      }
    }
  };

  ngOnInit() {
    const documentationUrl = this.apiGroup.apiInfoUrl ? this.apiGroup.url + this.apiGroup.apiInfoUrl : this.apiGroup.url;
    this.apiService.getApiInfo(documentationUrl).subscribe(response => {
      console.log(response);
      this.znodes = response;
      this.znodes.forEach(node => {
        node.open = true;
        node.path = `${this.apiPrefix}${node.path}`;
      });

    });
  }

  private onCheckHandler(event, treeId, treeNode) {
    const ztree = this.permTree.ztree;
    const checkedNodes = ztree.getCheckedNodes(true);
    const expObj = AppPermissionUtils.genExpObj(checkedNodes);
    setTimeout(() => {
      this.expStr = AppPermissionUtils.expObjToString(expObj);
    });
  }

  private checkNodesByExp(exp) {
    const ztree = this.permTree.ztree;
    if (!ztree) {
      return;
    }
    this.treeId = ztree.treeId;

    ztree.checkAllNodes(false);
    const expObj = typeof exp === 'object' ? exp : AppPermissionUtils.parseExpObj(exp);
    if (!expObj) {
      return;
    }

    const allNodes = ztree.transformToArray(ztree.getNodes());
    const allPathNodes = allNodes.filter(function (t) {
      return t.type === 'PATH';
    });
    for (const path in expObj) {
      const pathNodes = allPathNodes.filter(t => AppPermissionUtils.antPathRequestMatch(path, expObj[path], t.path, t.method));
      pathNodes.forEach(node => {
        ztree.checkNode(node, true, true);
      });
    }
  }

  public onInitialized() {
    this.applicationService.getAppPermission(this.appId).subscribe(response => {
      if (response) {
        this.expStr = response.filter(p => p.groupId === this.apiGroup.id).map(p => p.permission).join('\n');

        this.checkNodesByExp(this.expStr);
      }
    });
  }

  public save() {
    const permissions = this.expStr.split('\n').filter(p => !!p);
    this.applicationService.updateAppPermission(this.appId, this.apiGroup.id, permissions).subscribe(response => {
      this.alertService.alertInfo('保存成功！');
      this.isConfirmed = true;
      this.modalRef.hide();
    });
  }
}
