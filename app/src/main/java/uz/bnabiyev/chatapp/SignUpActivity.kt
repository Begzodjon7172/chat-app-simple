package uz.bnabiyev.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.bnabiyev.chatapp.databinding.ActivitySignUpBinding
import uz.bnabiyev.chatapp.models.User

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users")

        binding.apply {
            signUpBtn.setOnClickListener {
                if (isValid()){
                    val name = edtName.text.toString()
                    val email = edtEmail.text.toString()
                    val password = edtPassword.text.toString()
                    signUp(name, email, password)
                    edtEmail.setText("")
                    edtName.setText("")
                    edtPassword.setText("")
                }
            }
        }


    }

    private fun signUp(name: String, email: String, password: String) {
        // logic of creating user

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(name, email, auth.currentUser?.uid)

                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun isValid(): Boolean {
        binding.apply {
            if (edtEmail.text.toString() == "") {
                Toast.makeText(this@SignUpActivity, "Email kiritilmagan", Toast.LENGTH_SHORT).show()
                return false
            } else if (edtPassword.text.toString() == "") {
                Toast.makeText(this@SignUpActivity, "Password kiritilmagan", Toast.LENGTH_SHORT)
                    .show()
                return false
            } else if (edtName.text.toString() == "") {
                Toast.makeText(this@SignUpActivity, "Name kiritilmagan", Toast.LENGTH_SHORT).show()
                return false
            } else return true
        }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {
        if (uid != null) {
            reference.child(uid).setValue(User(name, email, uid))
        }
    }
}