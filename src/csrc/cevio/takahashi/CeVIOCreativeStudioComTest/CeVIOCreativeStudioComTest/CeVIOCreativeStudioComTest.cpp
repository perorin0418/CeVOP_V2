#include "stdafx.h"
#include <ObjBase.h>
#include <string>
#include <iostream>
using namespace std;

// �^�C�v���C�u�����C���|�[�g
// �i�^�C�v���C�u�����̓o�^�́A�yCeVIO Creative Studio�z�C���X�g�[�����ɍs���܂��B�j
#import "libid:D3AEA482-B527-4818-8CEA-810AFFCB24B6" named_guids rename_namespace("CeVIO")

int main(int argc, char *argv[])
{
	string cast        = "�^�J�n�V";
	string param1_name = "���C";
	string param2_name = "����";
	string param3_name = "�ւ���";
	string param4_name = "";
	
	int volume       = 0;
	int speed        = 0;
	int tone         = 0;
	int alpha        = 0;
	int toneScale    = 0;
	int param1       = 0;
	int param2       = 0;
	int param3       = 0;
	int param4       = 0;
	string text      = "";
	int isSave       = 0;
	string savePath  = "";

	// ����������Ă���ꍇ�͏I��
	if (argc != 13) {
		cout << "����������܂��� : "<< argc << endl;
		return -1;
	}
	
	// �R�}���h���C�������擾
	string _volume    = argv[1];
	string _speed     = argv[2];
	string _tone      = argv[3];
	string _alpha     = argv[4];
	string _toneScale = argv[5];
	string _param1    = argv[6];
	string _param2    = argv[7];
	string _param3    = argv[8];
	string _param4    = argv[9];
	string _text      = argv[10];
	string _isSave    = argv[11];
	string _savePath  = argv[12];

	volume = stoi(_volume);
	speed = stoi(_speed);
	tone = stoi(_tone);
	alpha = stoi(_alpha);
	toneScale = stoi(_toneScale);
	param1 = stoi(_param1);
	param2 = stoi(_param2);
	param3 = stoi(_param3);
	param4 = stoi(_param4);
	text = _text;
	isSave = stoi(_isSave);
	savePath = _savePath;

	cout << "volume => " << volume << endl;
	cout << "speed => " << speed << endl;
	cout << "tone => " << tone << endl;
	cout << "alpha => " << alpha << endl;
	cout << "toneScale => " << toneScale << endl;
	cout << "param1 => " << param1 << endl;
	cout << "param2 => " << param2 << endl;
	cout << "param3 => " << param3 << endl;
	cout << "param4 => " << param4 << endl;
	cout << "text => " << text << endl;
	cout << "isSave => " << isSave << endl;
	cout << "savePath => " << savePath << endl;

	// COM������
	::CoInitialize(NULL);

	// ServiceControl�C���X�^���X����
	CeVIO::IServiceControl *pServiceControl;
	HRESULT result0 = ::CoCreateInstance(CeVIO::CLSID_ServiceControl,
		NULL,
		CLSCTX_INPROC_SERVER,
		CeVIO::IID_IServiceControl,
		reinterpret_cast<LPVOID *>(&pServiceControl));
	if (FAILED(result0)) {
		// ���s
		::CoUninitialize();
		return -1;
	}

	// ServiceControl�̃o�[�W����4.0�ǉ��@�\�C���^�[�t�F�C�X���擾
	CeVIO::IServiceControlV40Part *pServiceControlV40Part = NULL;
	HRESULT result = pServiceControl->QueryInterface(CeVIO::IID_IServiceControlV40Part,
		reinterpret_cast<LPVOID *>(&pServiceControlV40Part));

	// �yCeVIO Creative Studio�z�N��
	pServiceControl->StartHost(false);

	// Talker�C���X�^���X����
	CeVIO::ITalker *pTalker;
	HRESULT result1 = ::CoCreateInstance(CeVIO::CLSID_Talker,
		NULL,
		CLSCTX_INPROC_SERVER,
		CeVIO::IID_ITalker,
		reinterpret_cast<LPVOID *>(&pTalker));
	if (FAILED(result1)) {
		// ���s
		::CoUninitialize();
		return -1;
	}

	// �L���X�g�ݒ�
	pTalker->Cast = cast.c_str();

	// ���ʐݒ�
	pTalker->Volume = volume;

	// �b���ݒ�
	pTalker->Speed = speed;

	// �����ݒ�
	pTalker->Tone = tone;

	// �����ݒ�
	pTalker->Alpha = alpha;

	if (param1 != -1) {
		// �p�����[�^�[1�ݒ�
		pTalker->Components->ByName(param1_name.c_str())->Value = param1;
	}

	if (param2 != -1) {
		// �p�����[�^�[2�ݒ�
		pTalker->Components->ByName(param2_name.c_str())->Value = param2;
	}

	if (param3 != -1) {
		// �p�����[�^�[3�ݒ�
		pTalker->Components->ByName(param3_name.c_str())->Value = param3;
	}

	if (param4 != -1) {
		// �p�����[�^�[4�ݒ�
		pTalker->Components->ByName(param4_name.c_str())->Value = param4;
	}

	// ServiceControl�̃o�[�W����4.0�ǉ��@�\�C���^�[�t�F�C�X���擾
	CeVIO::ITalkerV40Part *pTalkerV40Part = NULL;
	result = pTalker->QueryInterface(CeVIO::IID_ITalkerV40Part,
		reinterpret_cast<LPVOID *>(&pTalkerV40Part));
	if (SUCCEEDED(result)) {
		// �i��j�}�g�ݒ�
		pTalkerV40Part->ToneScale = toneScale;
	}

	if (isSave == 1) {
		boolean ret = pTalker->OutputWaveToFile(text.c_str(), savePath.c_str());
		if (!ret) {
			::CoUninitialize();
			return -1;
		}
	}else{
		// �Đ�
		CeVIO::ISpeakingStatePtr pState = pTalker->Speak(text.c_str());
		pState->Wait();
	}

	// Talker����i�ǉ������j
	if (pTalkerV40Part != NULL) pTalkerV40Part->Release();

	// Talker���
	pTalker->Release();

	// �yCeVIO Creative Studio�z�I��
	//pServiceControl->CloseHost(0);

	// ServiceControl����i�ǉ������j
	if (pServiceControlV40Part != NULL) pServiceControlV40Part->Release();

	// ServiceControl���
	pServiceControl->Release();

	// COM�g�p�I��
	::CoUninitialize();

	return 0;
}