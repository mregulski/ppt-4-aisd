#ifndef QUEUE
#define QUEUE 1
#include <stdbool.h>
#include <stdlib.h>
typedef struct node {
    int value;
    struct node *next;
} Node;

typedef struct {
    Node *top;
    Node *end;
} Queue;

Queue *q_new();
void q_free(Queue *q);
bool q_isEmpty(Queue *q);
void q_push(Queue *q, int x);
int q_pop(Queue *q);
int q_peek(Queue *q);
#endif
