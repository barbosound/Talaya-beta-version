package com.example.pau.talaya;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reserva.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reserva#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reserva extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private pendents Fpendents = new pendents();
    private finalitzades Ffinalitzades = new finalitzades();
    private favorits Ffavorits = new favorits();

    private home myContext;

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> idReserva = new ArrayList<>();
    private ArrayList<String> nom = new ArrayList<>();
    private ArrayList<String> DataEntrada = new ArrayList<>();
    private ArrayList<String> DataSortida = new ArrayList<>();
    private ArrayList<String> FKUsuari = new ArrayList<>();
    private ArrayList<String> FKCasa = new ArrayList<>();
    private ArrayList<String> Estat = new ArrayList<>();

    private ArrayList<String> idReservaFin;
    private ArrayList<String> preuReservaFin;
    private ArrayList<String> DataEntradaFin;
    private ArrayList<String> DataSortidaFin;
    private ArrayList<String> FKUsuariFin;
    private ArrayList<String> FKCasaFin;
    private ArrayList<String> EstatFin;

    private Bundle bPen = new Bundle();
    private Bundle bFin = new Bundle();

    public Reserva() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reserva.
     */
    // TODO: Rename and change types and number of parameters
    public static Reserva newInstance(String param1, String param2) {
        Reserva fragment = new Reserva();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            idReserva = getArguments().getStringArrayList("id");
            DataEntrada = getArguments().getStringArrayList("DE");
            DataSortida = getArguments().getStringArrayList("DS");
            FKUsuari = getArguments().getStringArrayList("FKUsuari");
            FKCasa = getArguments().getStringArrayList("FKCasa");
            Estat = getArguments().getStringArrayList("Estat");

            idReservaFin = getArguments().getStringArrayList("idFin");
            DataEntradaFin = getArguments().getStringArrayList("DEFin");
            DataSortidaFin = getArguments().getStringArrayList("DSFin");
            FKUsuariFin = getArguments().getStringArrayList("FKUsuariFin");
            FKCasaFin = getArguments().getStringArrayList("FKCasaFin");
            EstatFin = getArguments().getStringArrayList("EstatFin");


            bPen.putStringArrayList("id",idReserva);
            bPen.putStringArrayList("DE",DataEntrada);
            bPen.putStringArrayList("DS",DataSortida);
            bPen.putStringArrayList("FKUsuari",FKUsuari);
            bPen.putStringArrayList("FKCasa",FKCasa);
            bPen.putStringArrayList("Estat",Estat);

            bFin.putStringArrayList("id",idReservaFin);
            bFin.putStringArrayList("DE",DataEntradaFin);
            bFin.putStringArrayList("DS",DataSortidaFin);
            bFin.putStringArrayList("FKUsuari",FKUsuariFin);
            bFin.putStringArrayList("FKCasa",FKCasaFin);
            bFin.putStringArrayList("Estat",EstatFin);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setupViewPager(ViewPager viewPager) {
        MIFragmentPageManager adapter = new MIFragmentPageManager(getChildFragmentManager());

        Fpendents.setArguments(bPen);
        adapter.addFragment(Fpendents, "Pendents");

        Ffinalitzades.setArguments(bFin);
        adapter.addFragment(Ffinalitzades, "Finalitzades");

        adapter.addFragment(Ffavorits, "Favorits");

        viewPager.setAdapter(adapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class MIFragmentPageManager extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        FragmentManager fM;

        public MIFragmentPageManager(FragmentManager fm) {
            super(fm);

            fM = fm;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);

        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return fragmentTitleList.get(position);
        }
    }

}
