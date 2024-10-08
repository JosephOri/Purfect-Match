import android.os.Bundle
import okhttp3.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.purfectmatch.Model.Entities.PostEntity
import com.purfectmatch.R
import okhttp3.*
import java.io.IOException
import org.json.JSONArray

class SinglePostCardFragment : Fragment() {

    private lateinit var post: PostEntity
    private lateinit var image: ImageView
    private lateinit var kindTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var aboutTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var phoneNumberTextView: TextView
    private lateinit var ownerTextView: TextView
    private lateinit var apiTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.single_post_card, container, false)

        // Get the PostEntity object from the arguments
        post = arguments?.getSerializable("post") as PostEntity

        // Find the views in the layout
        image = view.findViewById(R.id.post_image)
        kindTextView = view.findViewById(R.id.post_kind)
        ageTextView = view.findViewById(R.id.post_age)
        aboutTextView = view.findViewById(R.id.post_about)
        locationTextView = view.findViewById(R.id.post_location)
        phoneNumberTextView = view.findViewById(R.id.post_phone)
        ownerTextView = view.findViewById(R.id.post_owner)
        apiTextView = view.findViewById(R.id.Api)

        // Set the values to the views
        Glide.with(requireContext())
            .load(post.img)
            .into(image)

        kindTextView.text = post.kind
        ageTextView.text = post.age
        aboutTextView.text = post.about
        locationTextView.text = post.location
        phoneNumberTextView.text = post.phone
        ownerTextView.text = post.owner

        // Call the API to fetch facts about the specific cat breed
        fetchCatFacts(post.kind)

        // Inflate the layout for this fragment
        return view
    }

    private fun fetchCatFacts(breed: String) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://catbreeddb.p.rapidapi.com/?search=$breed")
            .get()
            .addHeader("X-RapidAPI-Key", "06626f8afdmsh30f0ef8d77107c0p105612jsn2e455cd21766")
            .addHeader("X-RapidAPI-Host", "catbreeddb.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                // Update UI with the modified response data
                activity?.runOnUiThread {
                    // Format the response and update TextView

                    apiTextView.text = formatCatFacts(responseData)
                    if (apiTextView.text.isEmpty()){
                        apiTextView.text= "This breed not found"
                    }
                }
            }
        })
    }

    private fun formatCatFacts(responseData: String?): String {
        if (responseData.isNullOrEmpty()) return "No data available"

        val jsonArray = JSONArray(responseData)

        val formattedData = StringBuilder()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            // Extract relevant fields from the JSON object
            val funFact = jsonObject.getString("breedDescription")
            // Append formatted data to the string builder
            formattedData.append(" $funFact\n")
        }

        return formattedData.toString()
    }
}
