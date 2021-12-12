import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { from } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SnackbarService {
  constructor(private snackbar: MatSnackBar) {}

  public openSnackbar(message: string, action: string) {
    this.snackbar.open(message, action, { duration: 2000 });
  }
}
