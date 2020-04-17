import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  private url = '$applications';

  constructor(private http: HttpClient) {
  }

  public getAll(): Observable<any> {
    return this.http.get(`${this.url}`);
  }

  public save(application): Observable<any> {
    return this.http.post(`${this.url}`, application);
  }

  public delete(appId): Observable<any> {
    return this.http.delete(`${this.url}/${appId}`);
  }

  public getAppPermission(appId: string): Observable<any> {
    return this.http.get(`${this.url}/${appId}/permissions`);
  }

  public updateAppPermission(appId: string, groupId: string, permissionsStr: string[]): Observable<any> {
    return this.http.put(`${this.url}/${appId}/permissions`, permissionsStr, {params: {groupId: groupId}});
  }
}
