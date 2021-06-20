package com.rsschool.quiz.fragmentClasses

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.rsschool.quiz.applicationLogicClasses.Quiz
import com.rsschool.quiz.R
import com.rsschool.quiz.applicationLogicClasses.BundleValues
import com.rsschool.quiz.databinding.FragmentQuizResultBinding

class FragmentQuizResult : Fragment() {
    private var _quiz: Quiz? = null
    private val quiz get() = _quiz!!

    private var _binding: FragmentQuizResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _quiz = it.getParcelable(QUIZ)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizResultBinding.inflate(layoutInflater)
        showTheResultOfQuiz()
        binding.buttonShareResultOfQuiz.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, resources.getText(R.string.share_to_intent_subject))
                putExtra(Intent.EXTRA_TEXT, quiz.getQuizResultReportFormattedInText())
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.share_to_intent_title)))
        }
        binding.buttonRepeatQuiz.setOnClickListener {
            quiz.shuffleAndDropAnswersOfQuestions()
            val bundle = Bundle()
            bundle.putParcelable(QUIZ, quiz)
            it
                .findNavController()
                .navigate(
                    R.id.action_fragmentQuizResult_to_fragmentQuizQuestion,
                    bundle
                )
        }
        binding.buttonExit.setOnClickListener {
            activity?.finishAndRemoveTask()
        }
        return binding.root
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
    private fun showTheResultOfQuiz() {
        val countOfRightQuestions = quiz.getCountOfRightAnswers()
        val countOfQuestions = quiz.getCountQuestions()
        val percentOfRightQuestions = countOfRightQuestions.toDouble() / countOfQuestions * 100
        binding.textViewResultQuiz.text = "${getString(R.string.text_view_result)}\n $countOfRightQuestions out of $countOfQuestions (${percentOfRightQuestions.format(1)}%)"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val QUIZ = BundleValues.QUIZ

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentQuizResult().apply {
                arguments = Bundle().apply {

                }
            }
    }
}