import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SnackbarService } from 'src/app/services/snackbar.service';

const BACKEND_API_URL = environment.backendApiUrl;

@Component({
  selector: 'app-statement-of-bank-account-upload',
  templateUrl: './statement-of-bank-account-upload.component.html',
  styleUrls: ['./statement-of-bank-account-upload.component.css'],
})
export class StatementOfBankAccountUploadComponent implements OnInit {
  file: any;

  constructor(private http: HttpClient, private snackbar: SnackbarService) {}

  ngOnInit(): void {}

  handleFileInput(event: any) {
    this.file = event.files[0];
  }

  onSubmit(): void {
    let formData = new FormData();
    formData.append('file', this.file);

    this.http
      .post<any>(
        BACKEND_API_URL + '/statement-of-bank-account/upload',
        formData
      )
      .subscribe(
        (response) => {
          this.snackbar.openSnackbar(response['message'], 'OK');
        },
        (error) => {
          console.log(error);
        }
      );
  }
}
