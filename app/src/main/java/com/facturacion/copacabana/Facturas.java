package com.facturacion.copacabana;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class Facturas extends AppCompatActivity {
    Button btn_create;
    EditText customerName, invoiceDate, totalAmount, description;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturas);

        firestore = FirebaseFirestore.getInstance();

        // Encontrar las vistas
        customerName = findViewById(R.id.customer_name);
        invoiceDate = findViewById(R.id.invoice_date);
        totalAmount = findViewById(R.id.total_amount);
        description = findViewById(R.id.description);
        btn_create = findViewById(R.id.btn_create_invoice);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = customerName.getText().toString().trim();
                String date = invoiceDate.getText().toString().trim();
                String amount = totalAmount.getText().toString().trim();
                String desc = description.getText().toString().trim();

                if (name.isEmpty() || date.isEmpty() || amount.isEmpty() || desc.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    createInvoice(name, date, amount, desc);
                }
            }
        });
    }

    private void createInvoice(String name, String date, String amount, String desc) {
        Map<String, Object> invoiceData = new HashMap<>();
        invoiceData.put("customer_name", name);
        invoiceData.put("invoice_date", date);
        invoiceData.put("total_amount", amount);
        invoiceData.put("description", desc);

        // Añadir más campos de acuerdo a las necesidades (por ejemplo, impuestos, número de factura, etc.)

        firestore.collection("facturas").add(invoiceData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Factura creada exitosamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error al crear factura", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
