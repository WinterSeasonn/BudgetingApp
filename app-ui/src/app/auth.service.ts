import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, catchError, of} from 'rxjs';
import {Account} from './account';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  session: any;
  username!: string;
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private router: Router, private http: HttpClient) {
    let session: any = localStorage.getItem('session');
    if(session){
      session = JSON.parse(session);
    }
    this.session = session;
  }

  verify(username: string, password: string): Observable<Account>{
    const url = `http://localhost:8080/login/?user=${username}&pass=${password}`;
    this.username = username;
    return this.http.get<Account>(url).pipe(
      catchError(this.handleError<Account>('not valid'))
    )
  }

  logged() {
    return (this.session as Account);
  }

  login(account: Account){
    const url = `http://localhost:8080/login?user=${account.username}&pass=${account.password}`;
    this.session = account;
    localStorage.setItem('session',JSON.stringify(this.session));
    this.http.post<Account>(url, null, this.httpOptions).pipe(catchError(this.handleError<Account>('not valid'))
    ).subscribe();
    return account;
  }

  signup(username: string, password: string) : Observable<boolean> {
    const url = `http://localhost:8080/login/signup?user=${username}&pass=${password}`;
    return this.http.post<boolean>(url,null)
    .pipe(catchError(this.handleError<boolean>('not valid'))
    );
  }

  logout(){
    this.session = undefined;
    localStorage.removeItem("session");
    this.router.navigateByUrl("/");

    this.http.get(`http://localhost:8080/login/logout`).subscribe();
  }
  
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
