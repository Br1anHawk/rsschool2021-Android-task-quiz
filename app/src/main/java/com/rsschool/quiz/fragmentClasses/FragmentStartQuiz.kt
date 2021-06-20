package com.rsschool.quiz.fragmentClasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.rsschool.quiz.applicationLogicClasses.Quiz
import com.rsschool.quiz.R
import com.rsschool.quiz.applicationLogicClasses.BundleValues
import com.rsschool.quiz.databinding.FragmentStartQuizBinding

class FragmentStartQuiz : Fragment() {

    private var _binding: FragmentStartQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartQuizBinding.inflate(layoutInflater)
        val buttonStartQuiz = binding.buttonStartQuiz
        buttonStartQuiz.setOnClickListener {
            val bundle = Bundle()
            quiz.shuffleAndDropAnswersOfQuestions()
            bundle.putParcelable(QUIZ, quiz)
            it.findNavController().navigate(R.id.action_fragmentStartQuiz_to_fragmentQuizQuestion, bundle)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val QUIZ = BundleValues.QUIZ
        private val quiz = Quiz()

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentStartQuiz().apply {
                arguments = Bundle().apply {

                }
            }
    }
}