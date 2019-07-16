package com.vicky.apps.datapoints.ui.viewmodel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vicky.apps.datapoints.common.SchedulerProvider
import com.vicky.apps.datapoints.data.remote.Repository

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {


    @Mock
    lateinit var repository: Repository

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(repository, schedulerProvider)

       viewModel.setData(getObject())
    }

    @After
    fun tearDown() {
    }






    @Test
    fun getDataFromRemote() {

        Mockito.`when`(repository.getDataFromApi()).thenReturn(Single.just(getObject()))

        val testObserver = TestObserver<List<NewsDataList>>()

        viewModel.generateApiCall()
            .subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue { responseData -> responseData.size == 2 }
    }


    @Test
    fun sortToPopular(){
        viewModel.sortToPopular()

        assert(viewModel.getData()[0].rank == 1)
    }

    @Test
    fun sortToRecents(){
        viewModel.sortToRecents()

        val check = viewModel.getData()[0].timeCreated!!.compareTo(viewModel.getData()[1].timeCreated!!)

        assert(check == 1)
    }


    fun getObject(): List<NewsDataList>{
        val token = object : TypeToken<List<NewsDataList>>() {

        }
        val jsonData = "[\n" +
                "  {\n" +
                "    \"id\": \"121\",\n" +
                "    \"title\": \"Carousell is launching its own digital wallet to improve payments for its users\",\n" +
                "    \"description\": \"Due to launch next month in Singapore, CarouPay will allow buyers and sellers to complete transactions without leaving the Carousell app, rather than having to rely on third-party platforms or doing meet-ups to hand over cash. CarouPay will be a digital wallet within the Carousell app. \\\"More than half of our sellers will end up buying items as well, so maybe it makes sense to have that money in the wallet for purchases\\\" - Quek tells Tech in Asia.\",\n" +
                "    \"banner_url\": \"https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-siu-rui-ceo-tia-sg-2018.jpg\",\n" +
                "    \"time_created\": 1532853058,\n" +
                "    \"rank\": 2\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"122\",\n" +
                "    \"title\": \"Southeast Asia-based mobile listings startup Carousell raises \$85M\",\n" +
                "    \"description\": \"Carousell, the Singapore-based mobile listing service that operates across Southeast Asia, has pulled in an \$85 million Series C fund as it seeks to strengthen its business among the region's competitive e-commerce landscape before expanding globally.\",\n" +
                "    \"banner_url\": \"https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-hero-image_10june.png\",\n" +
                "    \"time_created\": 1532939458,\n" +
                "    \"rank\": 5\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"123\",\n" +
                "    \"title\": \"Tour de France: Geraint Thomas wins as Chris Froome finishes third\",\n" +
                "    \"description\": \"The Team Sky rider, 32, follows Sir Bradley Wiggins in 2012 and four-time Tour champion Chris Froome as Britain celebrates a sixth win in seven years. Alexander Kristoff won the final sprint finish on the Champs-Elysees as Thomas crossed the line arm-in-arm with Froome after three weeks of racing.\",\n" +
                "    \"banner_url\": \"https://storage.googleapis.com/carousell-interview-assets/android/images/_102749437_thomas_epa.jpg\",\n" +
                "    \"time_created\": 1530322670,\n" +
                "    \"rank\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"124\",\n" +
                "    \"title\": \"How to Grow Crops on Mars Before Humans Colonize the Red Planet\",\n" +
                "    \"description\": \"Preparations are already underway for missions that will land humans on Mars in a decade or so. But what would people eat if these missions eventually lead to the permanent colonization of the red planet?\",\n" +
                "    \"banner_url\": \"https://storage.googleapis.com/carousell-interview-assets/android/images/humansonmarsjpg.jpeg\",\n" +
                "    \"time_created\": 1519983341,\n" +
                "    \"rank\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"125\",\n" +
                "    \"title\": \"NASA's New Planet Hunter Begins Its Search for Alien Worlds\",\n" +
                "    \"description\": \"The Transiting Exoplanet Survey Satellite (TESS), which is designed to hunt for alien worlds around stars not too far from the sun, began gathering science data Wednesday (July 25), members of the instrument team announced yesterday (July 27).\",\n" +
                "    \"banner_url\": \"https://storage.googleapis.com/carousell-interview-assets/android/images/nasa_alien_hunting.jpeg\",\n" +
                "    \"time_created\": 1527672941,\n" +
                "    \"rank\": 5\n" +
                "  },\n" +
                "    {\n" +
                "    \"id\": \"126\",\n" +
                "    \"title\": \"Carousell funding\",\n" +
                "    \"description\": \"Carousell, the Singapore-based mobile listing service that operates across Southeast Asia, has pulled in an \$85 million Series C fund as it seeks to strengthen its business among the region’s competitive e-commerce landscape before expanding globally.\",\n" +
                "    \"banner_url\": \"https://storage.googleapis.com/carousell-interview-assets/android/images/nasa_alien_hunting.jpeg\",\n" +
                "    \"time_created\": 1527672941,\n" +
                "    \"rank\": 7\n" +
                "  }\n" +
                "]"
        val gson = Gson()
        return gson.fromJson(jsonData,token.type)

    }





}