package uz.bnabiyev.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import uz.bnabiyev.chatapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            finish()
            startActivity(intent)
        }

        binding.apply {
            loginBtn.setOnClickListener {
                if (isValid()) {
                    val email = edtEmail.text.toString()
                    val password = edtPassword.text.toString()
                    login(email, password)
                    binding.edtEmail.setText("")
                    binding.edtPassword.setText("")
                }
            }
        }

    }

    private fun isValid(): Boolean {
        binding.apply {
            if (edtEmail.text.toString() == "") {
                Toast.makeText(this@LoginActivity, "Email kiritilmagan", Toast.LENGTH_SHORT).show()
                return false
            } else if (edtPassword.text.toString() == "") {
                Toast.makeText(this@LoginActivity, "Password kiritilmagan", Toast.LENGTH_SHORT)
                    .show()
                return false
            } else return true
        }
    }

    private fun login(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for loggin in user

                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        baseContext,
                        "User does not exist!!!",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }


    }
}